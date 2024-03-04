<?php
$servername = "172.30.116.25";
$username = "root";
$password = "";

// Create connection
$conn = mysqli_connect($servername, $username, $password);

// Check connection
if (!$conn) {
  die("Connection failed: " . mysqli_connect_error());
}
echo "Connected successfully";
?> 
<DOCTYPE html>
<html>
  <head>
    <title>PHP Test</title>
  </head>
  <body>
    <h1>PHP Test</h1>
    <?php
    echo "If you see this, PHP is working!";
    ?>
  </body>