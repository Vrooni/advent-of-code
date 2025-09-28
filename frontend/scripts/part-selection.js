window.addEventListener("scroll", () => {
  localStorage.setItem("scrollPos", window.scrollY);
});

window.addEventListener("DOMContentLoaded", () => {
  const scrollPos = localStorage.getItem("scrollPos");
  if (scrollPos !== null) {
    window.scrollTo(0, parseInt(scrollPos, 10));
  }

  const part = document.querySelector(".tag-container .active");
  if (part) {
    if (part.classList.contains("one-tag")) {
      selectPartOne(part);
    } else if (activeEl.classList.contains("two-tag")) {
      selectPartTwo(part);
    }
  }
});

function stopAnimations(el) {
  if (el._currentAnimation) {
    el._currentAnimation.cancel();
    el._currentAnimation = null;
  }
}

function selectPartOne(element) {
  getPartSolution(1);

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

function selectPartTwo(element) {
  getPartSolution(2);

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

function getPartSolution(part) {
  const codeLang = document.getElementById("code-lang").innerText;

  if (codeLang === "PHP") {
    fetch("../components/solution-php.php?part=" + part)
      .then(response => response.text())
      .then(data => {
        document.getElementById("code-container").innerHTML = data;
        Prism.highlightAll();
      })
      .catch(error => console.error("Error while getting code solution:", error));
  } 

  else if (codeLang === "Java") {
    fetch("../components/solution-java.php?part=" + part)
      .then(response => response.text())
      .then(data => {
        document.getElementById("code-container").innerHTML = data;
        Prism.highlightAll();
      })
      .catch(error => console.error("Error while getting code solution:", error));
  }
}