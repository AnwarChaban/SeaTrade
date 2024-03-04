<!DOCTYPE html>
<html>
<head>
    <title>SeaTrade</title>
</head>
<body>
    
    

    <?php
        echo "<p>helldadadaso</p>";// Your PHP code goes here

        // Database connection parameters
        $host = '172.30.116.25:3306';
        $username = 'root';
        $password = '';
        $database = 'seatrade';

        // Create a connection
        $conn = mysqli_connect($host, $username, $password, $database);

        // Check connection
        if (!$conn) {
            die("Connection failed: " . mysqli_error($conn));
        } 

        if ($conn) {
            echo "hellooooo";
        }

       
        $sql = "SELECT * FROM company";

        // Process the retrieved data
        if ($conn) {
           
                    // Process each row of data here
                    // Example: echo $row['column_name'];
                    echo $row['CompanyName']; // Replace 'column_name' with the actual column name
                }
            else {
                echo "No data found.";
            }
        

        
?>
    

    

</body>
</html>
