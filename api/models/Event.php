<?php

class Event {
	private $conn;
	public $id;
	public $title;
	public $year;
	public $month;
	public $day;
	public $value;

	//Constructor with DB
	public function __construct($db){
		$this->conn = $db;
	}
	//" ".$this->hour.":".$this->minute.":00"
	public function Get(){
		$str = $this->year."-".$this->month."-".$this->day;
		$query = 'SELECT DISTINCT b.id, b.title, f.value FROM aubtv_content b, aubtv_fields_values f, 
		(SELECT a.item_id FROM aubtv_fields_values a, (SELECT item_id FROM aubtv_fields_values 
		WHERE field_id = 5 AND value = "Event") c WHERE a.value like "'. $str .'%") g 
		WHERE f.item_id = b.id AND f.item_id = g.item_id AND f.field_id = 1;';	
 		$stmt = $this->conn->prepare($query);
		$stmt->execute();
		return $stmt;
	}
}
?>
