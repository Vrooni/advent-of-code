<?php
session_start();
$selected_day = $_SESSION["selected_day"];
$selected_year = $_SESSION["selected_year"];

$part = $_GET["part"] ?? "1";
$_SESSION["part"] = $part;

$path_to_java_code = "../../backend/solutions/java-solutions/year$selected_year/Day$selected_day" . "_$part.java";
?>
<code class="language-java"><?php require($path_to_java_code); ?></code>