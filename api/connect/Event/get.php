<?php
// who can access authorization and token should be here
header('Access-Control-Allow-Origin: *') ;
header('Content-Type: application/json');

include_once '../../config/Database.php';
include_once '../../models/Event.php';


	// Instantiate database and connect
	$database = new Database();
	$db = $database->connect();
	$event= new Event($db);
	$event->year = isset($_GET['year']) ? $_GET['year'] : die();
	$event->month = isset($_GET['month']) ? $_GET['month'] : die();
	$event->day = isset($_GET['day']) ? $_GET['day'] : die();
	// $event->hour = isset($_GET['hour']) ? $_GET['hour'] : die();
	// $event->minute = isset($_GET['minute']) ? $_GET['minute'] : die();
	$result = $event->Get();
	$num = $result->rowCount();
	if ($num > 0){
		$event_array = array();
		$event_array['data'] = array();

		while($row = $result->fetch(PDO::FETCH_ASSOC)){
			extract($row);
			$event_item = array(
					'id' => $id,
					'title' => $title,
					'details' => $value,
				);
			array_push($event_array['data'], $event_item);
		}
		echo json_encode($event_array);
	}else{
		echo json_encode(array('message' => 'No Events Were Found'));
	}
?>