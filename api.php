<?php
/*** UNCOMMENT IN PRODUCTION ***/
///*
ini_set('error_reporting', E_ALL | E_STRICT); 
ini_set('display_errors', 'Off'); 
ini_set('log_errors', 'Off'); 
//*/
 header("Access-Control-Allow-Origin: *");
$conn = new mysqli("mysql.dsv.su.se", "joso8829", "vaeB3iebi9ro", "joso8829");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

// This is the API to possibility show the user list, and show a specific user by action.

function get_user_by_id($id)
{
  global $conn;
  
  if ($stmt = $conn->prepare("SELECT * FROM Users WHERE id=? LIMIT 1")) {

    /* bind parameters for markers */
    $stmt->bind_param("s", $id);

    /* execute query */
    $stmt->execute();
	$response = $stmt->get_result()->fetch_array(MYSQLI_ASSOC);
	if($response)
	  return responsify($response);
    return errorify("Could not find user");
  } else {
	  return errorify("Could not prepare SQL statement: ".$conn->error);
  }
}

function add_user()
{
  global $conn;
  $data = json_decode(file_get_contents('php://input'), true);
  if(!($data['id'] && isset($data['firstName']) && isset($data['middleName']) && isset($data['lastName']) && isset($data['name']) && $data['linkUri'])) {
	return errorify("Missing data for user creation: ".(!$data['id']?"id, ":'').(!isset($data['firstName'])?"firstName, ":'').(!isset($data['middleName'])?"middleName, ":'').(!isset($data['lastName'])?"lastName, ":'').(!isset($data['name'])?"name, ":'').(!$data['linkUri']?"linkUri, ":'')." || DATA RECIEVED: ".file_get_contents('php://input'));
  }
  if ($stmt = $conn->prepare("INSERT INTO Users (id, firstName, middleName, lastName, name, linkUri) VALUES (?, ?, ?, ?, ?, ?)")) {

    /* bind parameters for markers */
    $stmt->bind_param("ssssss", $data['id'],
								$data['firstName'],
								$data['middleName'], 
								$data['lastName'],
								$data['name'],
								$data['linkUri']);

    /* execute query */
	if($stmt->execute())
	  return responsify();
    return errorify("User already exists.");
  } else {
	  return errorify("Could not prepare SQL statement: ".$conn->error);
  }
}

function errorify($message) {
	return Array("error" => true, "message" => $message);
}

function responsify($data) {
	if($data)
		return Array("error" => false, "data" => $data);
	return Array("error" => false);
}

//$possible_url = array("get_user_list", "get_user");

$value = errorify("Unknown endpoint");

if (isset($_GET["action"]))
{
  $params = explode("/",$_GET["action"],2);
  switch ($params[0])
    {
      case "get_user":
        $value = get_user_by_id($params[1]);
        break;
	  case "add_user":
        $value = add_user($params[1]);
        break;
    }
}

$conn->close();
exit(json_encode($value));

?>
