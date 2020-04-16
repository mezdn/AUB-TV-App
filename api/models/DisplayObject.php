<?php

class DisplayObject {
	private $conn;
	public $id;
	public $title;
	public $description;
	public $category;	
	public $type;
	public $imageUrls;
	public $youtubeId;

	//Constructor with DB
	public function __construct($db){
		$this->conn = $db;
	}

	public function Get(){
		$query = 'SELECT j.id, j.title, j.category , j.description, j.type, j.imageUrls, k.value 
		as youtubeId FROM (SELECT l.id, l.title, l.category , l.description, l.type, s.value as 
		imageUrls FROM (SELECT m.id, m.title, m.category , m.value as description, h.value as 
		type FROM (SELECT c.id, c.title, c.category , d.value FROM (SELECT a.id, a.title, b.title as
		category FROM aubtv_content a, aubtv_categories b WHERE a.catid = b.id AND a.id = ?) c 
		LEFT JOIN (SELECT item_id, value FROM aubtv_fields_values WHERE item_id = ? AND field_id = 1) 
		d ON c.id = d.item_id) m LEFT JOIN (SELECT item_id, value FROM aubtv_fields_values WHERE 
		item_id = ? AND field_id = 5) h ON m.id = h.item_id) l LEFT JOIN (SELECT item_id, value FROM 
		aubtv_fields_values WHERE item_id = ? AND field_id = 3) s ON l.id = s.item_id) j LEFT JOIN 
		(SELECT item_id, value FROM aubtv_fields_values WHERE item_id = ? AND field_id = 2) K 
		ON j.id = k.item_id';
		$stmt = $this->conn->prepare($query);
		$stmt->bindParam(1, $this->id);
		$stmt->bindParam(2, $this->id);
		$stmt->bindParam(3, $this->id);
		$stmt->bindParam(4, $this->id);
		$stmt->bindParam(5, $this->id);
		$stmt->execute();
		return $stmt;
	}
	public function GetByCategory(){
		$query = 'SELECT j.id, j.title, j.category , j.description, j.type, j.imageUrls, k.value 
		as youtubeId FROM (SELECT l.id, l.title, l.category , l.description, l.type, s.value as imageUrls
		FROM (SELECT m.id, m.title, m.category , m.value as description, h.value as type FROM 
		(SELECT c.id, c.title, c.category , d.value FROM (SELECT a.id, a.title, b.title as category
		FROM aubtv_content a, aubtv_categories b WHERE a.catid = b.id AND b.title != "None" AND b.title = ?) c 
		LEFT JOIN (SELECT item_id, value FROM aubtv_fields_values WHERE field_id = 1) d ON 
		c.id = d.item_id) m LEFT JOIN (SELECT item_id, value FROM aubtv_fields_values WHERE 
		field_id = 5) h ON m.id = h.item_id) l LEFT JOIN (SELECT item_id, value FROM 
		aubtv_fields_values WHERE field_id = 3) s ON l.id = s.item_id) j LEFT JOIN 
		(SELECT item_id, value FROM aubtv_fields_values WHERE field_id = 2) K ON j.id = k.item_id 
		ORDER BY j.category, j.id DESC  LIMIT 0,50';
		$stmt = $this->conn->prepare($query);
		$stmt->bindParam(1, $this->category);
		$stmt->execute();
		return $stmt;
	}
}

?>
