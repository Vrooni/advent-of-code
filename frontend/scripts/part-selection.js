window.addEventListener("DOMContentLoaded", () => {
  const year = document.getElementById("year").textContent.trim();
  const day = document.getElementById("day").textContent.trim();
  const part = document.querySelector(".tag-container .active");
  if (part) {
    if (part.classList.contains("one-tag")) {
      selectPartOne(part, year, day);
    } else if (part.classList.contains("two-tag")) {
      selectPartTwo(part, );
    }
  }
});

function stopAnimations(el) {
  if (el._currentAnimation) {
    el._currentAnimation.cancel();
    el._currentAnimation = null;
  }
}

function selectPart(year, day) {
  const element = document.querySelector("img.active");
  if (element.classList.contains("one-tag")) {
    selectPartOne(element, year, day);
  } else {
    selectPartTwo(element, year, day);
  }
}

function selectPartOne(element, year, day) {
  getPartSolution(year, day, 1);

  element.classList.add("active");

  const other = element.nextElementSibling;
  other.classList.remove("active");
  stopAnimations(other);

  element._currentAnimation = element.animate(
    [
      { transform: "rotate(-15deg)" },
      { transform: "rotate(-5deg)" },
      { transform: "rotate(-23deg)" },
      { transform: "rotate(-8deg)" },
      { transform: "rotate(-20deg)" },
    ],
    {
      duration: 1800,
      easing: "ease-out"
    }
  );
}

function selectPartTwo(element, year, day) {
  getPartSolution(year, day, 2);

  element.classList.add("active");

  const other = element.previousElementSibling;
  other.classList.remove("active");
  stopAnimations(other);

  element._currentAnimation = element.animate(
    [
      { transform: "rotate(15deg)" },
      { transform: "rotate(5deg)" },
      { transform: "rotate(23deg)" },
      { transform: "rotate(8deg)" },
      { transform: "rotate(20deg)" },
    ],
    {
      duration: 1800,
      easing: "ease-out"
    }
  );
}

function getPartSolution(year, day, part) {
  const codeLang = document.getElementById("code-lang").textContent.trim();

  if (codeLang === "PHP") {
    fetch("frontend/components/solution-php.php?year=" + year + "&day=" + day + "&part=" + part)
      .then(response => response.text())
      .then(data => {
        document.getElementById("code-container").innerHTML = data;
        Prism.highlightAll();
      })
      .catch(error => console.error("Error while getting code solution:", error));
  }

  else if (codeLang === "Java") {
    fetch("frontend/components/solution-java.php?year=" + year + "&day=" + day + "&part=" + part)
      .then(response => response.text())
      .then(data => {
        document.getElementById("code-container").innerHTML = data;
        Prism.highlightAll();
      })
      .catch(error => console.error("Error while getting code solution: ", error));
  }
}