<?php
/*** UNCOMMENT IN PRODUCTION ***/
/*
ini_set('error_reporting', E_ALL | E_STRICT); 
ini_set('display_errors', 'Off'); 
ini_set('log_errors', 'Off'); 
//*/

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

function get_user_list()
{
  $user_list = array(array("id" => 1, "name" => "Simon"), array("id" => 2, "name" => "Zannetie"), array("id" => 3, "name" => "Carbonnel")); // call in db, here I make a list of 3 users.

  return $user_list;
}

function errorify($message) {
	return Array("error" => 1, "message" => $message);
}

function responsify($data) {
	return Array("error" => 0, "data" => $data);
}

//$possible_url = array("get_user_list", "get_user");

$value = errorify("Unknown endpoint");

if (isset($_GET["action"]))
{
  $params = explode("/",$_GET["action"],2);
  switch ($params[0])
    {
      case "get_user_list":
        $value = get_user_list($params[1]);
        break;
      case "get_user":
        $value = get_user_by_id($params[1]);
        break;
    }
}

$conn->close();
exit(json_encode($value));

?>
