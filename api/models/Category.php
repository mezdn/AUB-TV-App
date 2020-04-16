<?php

class Category {
	private $conn;
	public $id;
	public $title;
	public $auxname;

	//Constructor with DB
	public function __construct($db){
		$this->conn = $db;
	}

	public function Get(){
		$query = 'SELECT * FROM aubtv_categories WHERE id != 1 AND title != "None" ORDER BY title';
		$stmt = $this->conn->prepare($query);
		$stmt->execute();
		return $stmt;
	}
}
?>