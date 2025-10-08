<?php
class Utils
{
  static function java_solution_exists($year, $day, $part)
  {
    return self::get_java_solution($year, $day, $part);
  }

  static function get_java_solution($year, $day, $part)
  {
    $path_to_year = "backend/solutions/java-solutions/year$year";
    $path_to_code = "$path_to_year/Day$day" . "_$part.txt";

    if (file_exists($path_to_code)) {
      return file_get_contents($path_to_code);
    }

    $url = "http://localhost:8080/code?year=$year&day=$day&part=$part";

    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $result = curl_exec($ch);

    if (curl_getinfo($ch, CURLINFO_HTTP_CODE) == 200) {

      if (!file_exists($path_to_year)) {
        mkdir($path_to_year, 0777, true);
      }

      file_put_contents($path_to_code, $result);
      curl_close($ch);
      return $result;
    }

    curl_close($ch);
    return "";
  }
}
