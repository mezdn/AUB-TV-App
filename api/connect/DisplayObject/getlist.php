<?php
// who can access authorization and token should be here
header('Access-Control-Allow-Origin: *') ;
header('Content-Type: application/json');

include_once '../../config/Database.php';
include_once '../../models/DisplayObject.php';
include_once '../../models/Category.php';

	$database = new Database();
	$db = $database->connect();
	$category = new Category($db);
	$displayobject = new DisplayObject($db);
	$categories = $category->Get();
	$num_cat = $categories->rowCount();

	if($num_cat > 0){
		$displayobject_array = array();
		while ($row = $categories->fetch(PDO::FETCH_ASSOC)){
		extract($row);
		$displayobject->category = $title;
		$result = $displayobject->GetByCategory();
		$num = $result->rowCount();
		if ($num > 0){
			$displayobject_array[$displayobject->category] = array();
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
				array_push($displayobject_array[$displayobject->category], $displayobject_item);
			}
		}
		}
		echo json_encode($displayobject_array);
	}else{
		echo json_encode(array('message' => 'No Objects Were Found'));
	}
?>