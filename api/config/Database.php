<?php
class Database {
	private $host = 'mysql6002.site4now.net';
	private $db_name = 'db_a4c491_aubtvdb';
	private $user_name = 'a4c491_aubtvdb';
	private $password = 'AuubTvtV192y';
	private $conn;


	public function connect(){
		$this->conn = null;

		try {
			$this->conn = new PDO('mysql:host='.$this->host.';dbname='.$this->db_name,$this->user_name,$this->password);
			$this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		} catch (PDOException $e) {
			echo 'Connection Error: '. $e->getMessage();
		}
		return $this->conn;
	}
}

?>