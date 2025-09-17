window.addEventListener('resize', resizeDaysContainer);

function toggle(element) {
  if (element.classList.contains("closed")) {
    // close others
    document.querySelectorAll(".year-button").forEach(el => {
      el.classList.add("closed")
    });
    document.querySelectorAll(".days-container").forEach(el => {
      el.classList.add("hidden")
      el.style.maxHeight = "0px";
    });

    const days = element.nextElementSibling;
    element.classList.remove("closed");
    days.classList.remove("hidden");
    days.style.maxHeight = days.scrollHeight + "px"


  } else {
    element.classList.add("closed");
    element.nextElementSibling.classList.add("hidden");
    element.nextElementSibling.style.maxHeight = "0px";
  }
}

function resizeDaysContainer() {
  document.querySelectorAll(".days-container").forEach(el => {
    if (!el.classList.contains("hidden")) {
      el.style.maxHeight = el.scrollHeight + "px"
    }
  });
}