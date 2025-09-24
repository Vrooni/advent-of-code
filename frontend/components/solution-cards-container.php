<?php
$text = "";
$solution_part_1 = null;
$solution_part_2 = null;
$path_to_php_code = "../../backend/solutions/php-solutions/year$selected_year";
$path_to_java_code = "../../backend/solutions/java-solutions/year$selected_year";

if (isset($_POST["input"])) {
  $text = $_POST["input"];
  $input = preg_split("/\r\n|\n|\r/", trim($text));

  $solution_part_1 = get_solution($input, $selected_day, 1);
  $solution_part_2 = get_solution($input, $selected_day, 2);
}

function get_solution($input, $day, $part)
{
  global $path_to_php_code;
  global $path_to_java_code;
  $path_to_php_day = "$path_to_php_code/Day$day" . "_$part.php";
  $java_class = "Day$day" . "_$part";

  try {
    // first prio php
    if (file_exists($path_to_php_day)) {
      return ["success", require($path_to_php_day)];
    }

    // second prio java
    elseif (file_exists($path_to_java_code . "/$java_class.class")) {
      $args = implode(" ", $input);
      $result = shell_exec("java -cp $path_to_java_code $java_class $args");
      if ($result) {
        return ["success", $result];
      } else {
        return ["error", "Invalid input"];
      }
    }

    // no solution to run
    else {
      return ["error", "500 - Solution file not found"];
    }
  } catch (Throwable $e) {
    // TODO delete it
    // return ["error", $e->getMessage()];
    return ["error", "Invalid input"];
  }
}
?>

<div class="cards-container">
  <div class="card-container">
    <form method="post">
      <p>Enter your input:</p>
      <textarea class="textarea" name="input"><?php echo $text; ?></textarea>

      <div class="buttons">
        <a class="btn secondary"
          href="<?php echo "https://adventofcode.com/$selected_year/day/$selected_day" ?>"
          target="_blank"
          rel="noopener noreferrer">
          Problem
        </a>
        <button class="btn primary" type="submit" name="day-and-year" value="<?php echo "$selected_day:$selected_year" ?>">Solve</button>
      </div>
    </form>
  </div>

  <div class="output-container">
    <div onclick="copyToClipboard(this)" class="card-container output <?php echo isset($solution_part_1) ? $solution_part_1[0] : ""; ?> ">
      <p class="part-title">Part 1</p>
      <p class="part-solution"">
        <?php
        if (isset($solution_part_1)) {
          echo $solution_part_1[1];
        } else {
          echo "Answers will ";
        }
        ?>
      </p>
      <span class=" info">Copy</span>
    </div>

    <div onclick="copyToClipboard(this)" class="card-container output <?php echo isset($solution_part_2) ? $solution_part_2[0] : ""; ?> ">
      <p class="part-title">Part 2</p>
      <p class="part-solution"">
        <?php
        if (isset($solution_part_2)) {
          echo $solution_part_2[1];
        } else {
          echo " be shown here";
        }
        ?>
      </p>
      <span class=" info">Copy</span>
    </div>
  </div>

</div>

<div class="solution-container">
  <h2>Code Solution</h2>
  <!-- TODO make pages for java and php and for parts?? -->
  <!-- TODO make the files be able to download?? -->
  <?php
  if (
    file_exists("$path_to_php_code/Day$selected_day" . "_1.php") &&
    file_exists("$path_to_php_code/Day$selected_day" . "_2.php")
  ) {
    require("../components/solution-php.php");
  }

  if (
    file_exists("$path_to_java_code/Day$selected_day" . "_1.java") &&
    file_exists("$path_to_java_code/Day$selected_day" . "_2.java")
  ) {
    require("../components/solution-java.php");
  }
  ?>
</div>