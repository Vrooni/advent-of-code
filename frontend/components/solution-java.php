<?php
require_once("../utils/utils.php");

if (isset($_GET["year"]) && isset($_GET["day"]) && isset($_GET["part"])) {
  $selected_year = $_GET["year"];
  $selected_day = $_GET["day"];
  $part = $_GET["part"];

  $code = Utils::get_java_solution($selected_year, $selected_day, $part);
}
?>
<code class="language-java"><?php echo $code ?
                              htmlspecialchars($code) :
                              "404 - Solution not found"; ?></code>