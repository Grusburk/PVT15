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

function add_ID()
{
	global $conn;
	$data = json_decode(file_get_contents('php://input'), true);
	if(!($data['id'])) {
		return errorify("Missing data for ID creation: " .(!$data['id']?"id, ":'')." || DATA RECIEVED: ".file_get_contents('php://input'));		
	}
	if ($stmt = $conn->prepare("INSERT INTO Active_ID (id) VALUES(?) ON DUPLICATE KEY UPDATE timestamp = NOW()")){
		$stmt->bind_param("s", $data['id']);
		if($stmt->execute())
			return responsify();
		return errorify("ID already exists.");
	} else {
	  return errorify("Could not prepare SQL statement: ".$conn->error);
  }
}

function get_IDs()
{
	global $conn;
	$data = json_decode(file_get_contents('php://input'), true);
	$age = '00:30:00';
	$age = isset($data['age'])?$data['age']:$age;
	if ($stmt = $conn->prepare("SELECT id FROM Active_ID WHERE timestamp > SUBTIME(NOW(),?)")){
		$stmt->bind_param("s", $age);
		if($stmt->execute()) {
			$ids = Array();
			if($result = $stmt->get_result()) {
				while($response = $result->fetch_array(MYSQLI_ASSOC)) {
					array_push($ids, $response['id']);
				}
			}
			return responsify($ids);
		} else
			return errorify("ID already exists.");
		
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
		case "add_ID":
		$value = add_ID($params[1]);
        break;
		
		case "get_IDs":
		$value = get_IDs($params[1]);
        break;
    }
}

$conn->close();
exit(json_encode($value));

?>
