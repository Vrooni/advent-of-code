function stopAnimations(el) {
  if (el._currentAnimation) {
    el._currentAnimation.cancel();
    el._currentAnimation = null;
  }
}

function selectDayOne(element) {
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

function selectDayTwo(element) {
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
