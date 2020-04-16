<?php
// who can access authorization and token should be here
header('Access-Control-Allow-Origin: *') ;
header('Content-Type: application/json');

include_once '../../config/Database.php';
include_once '../../models/DisplayObject.php';

$database = new Database();
	$db = $database->connect();
	$displayobject= new DisplayObject($db);
	$displayobject->id = isset($_GET['id']) ? $_GET['id'] : die();
	$result = $displayobject->Get();
	$num = $result->rowCount();
	if ($num > 0){
		$displayobject_array = array();
		while($row = $result->fetch(PDO::FETCH_ASSOC)){
			extract($row);
			$displayobject_item = array(
					'id' => $id,
					'title' => $title,
					'description' => $description,
					'category' => $category,
					'type' => $type,
					'imageUrls' => $imageUrls,
					'youtubeId' => $youtubeId,
				);
			array_push($displayobject_array, $displayobject_item);
		}
		echo json_encode($displayobject_array);
	}else{
		echo json_encode(array('message' => 'No Object Was found with that Id.'));
	}
?>