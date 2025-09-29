const svg = document.querySelector(".lights");

addLights(svg, "stroke1");
addLights(svg, "stroke2");
addLights(svg, "stroke3");
addLights(svg, "stroke4");
addLights(svg, "stroke5");

function addLights(svg, id) {
  const stroke = document.getElementById(id);

  for (let pos = 0; pos < stroke.getTotalLength(); pos += 30) {

    const point = stroke.getPointAtLength(pos);
    const xOffset = (Math.random() - 0.5) * 20;
    const yOffset = (Math.random() - 0.5) * 30;
    const radius = 2 + Math.random() * 2
    const delay = (Math.random() * 2) + "s";

    const shadow = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
    shadow.setAttribute("class", "shadow");
    shadow.setAttribute("cx", point.x + xOffset);
    shadow.setAttribute("cy", point.y + yOffset);
    shadow.setAttribute("r", radius + 1);

    const light = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
    light.setAttribute("class", "light");
    light.setAttribute("cx", point.x + xOffset);
    light.setAttribute("cy", point.y + yOffset);
    light.setAttribute("r", radius);
    
    shadow.style.animation = "glimmer 1.5s infinite alternate";
    shadow.style.animationDelay = delay;

    light.style.animation = "glimmer 1.5s infinite alternate";
    light.style.animationDelay = delay;

    svg.appendChild(shadow);
    svg.appendChild(light);
  }
}