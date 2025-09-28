<?php
session_start();
$selected_day = $_SESSION["selected_day"];
$selected_year = $_SESSION["selected_year"];

$part = $_GET["part"] ?? "1";
$_SESSION["part"] = $part;

$path_to_php_code = "../../backend/solutions/php-solutions/year$selected_year/Day$selected_day" . "_$part.php";
?>
<code class="language-php"><?php echo htmlspecialchars(file_get_contents($path_to_php_code)); ?></code>