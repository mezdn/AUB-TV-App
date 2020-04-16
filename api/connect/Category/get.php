<?php
// who can access authorization and token should be here
header('Access-Control-Allow-Origin: *') ;
header('Content-Type: application/json');

include_once '../../config/Database.php';
include_once '../../models/Category.php';

	$database = new Database();
	$db = $database->connect();
	$category = new Category($db);
	$result = $category->Get();
	$num = $result->rowCount();
	if ($num > 0){
		$category_array = array();
		$category_array['data'] = array();

		while($row = $result->fetch(PDO::FETCH_ASSOC)){
			extract($row);
			$category_item = array(
					'id' => $id,
					'name' => $title,
				);
			array_push($category_array['data'], $category_item);
		}
		echo json_encode($category_array);
	}else{
		echo json_encode(array('message' => 'No Catgories Were Found'));
	}
?>