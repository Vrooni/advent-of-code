<?php
if (isset($_GET["year"]) && isset($_GET["day"]) && isset($_GET["part"])) {
  $selected_year = $_GET["year"];
  $selected_day = $_GET["day"];
  $part = $_GET["part"];

  $path_to_php_code = "../../backend/solutions/php-solutions/year$selected_year/Day$selected_day" . "_$part.php";
}
?>
<code class="language-php"><?php echo isset($path_to_php_code) ?
                              htmlspecialchars(file_get_contents($path_to_php_code)) :
                              "404 - Solution not found"; ?></code>