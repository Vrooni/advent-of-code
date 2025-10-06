function toggleLanguage() {
  const element = document.getElementById("code-lang");
  element.textContent = element.textContent.trim() === "PHP" ? "Java" : "PHP";
}