// Inicialization about updates section
const meetings = document.querySelectorAll(".update-item");
const details = document.querySelectorAll(".update-content");
let eriler = meetings.length-1;
details[meetings.length-1].style.display = 'block';

// Updates section logic
const btnPressed = e => {
    let tmp = e.target.id;
    console.log(tmp);
    console.log(details[tmp]);
    details[eriler].style.display = "none";
    details[tmp].style.display = "block"
    eriler = tmp;
}

for(let meat of meetings){
    meat.addEventListener("click", btnPressed);
}

// Toggle button in navabar
function toggleButton(){
    let minMenu = document.querySelector(".navamenu");
    if(minMenu.className === "navamenu"){
        minMenu.className += " responsive";
    } else {
        minMenu.className = "navamenu";
    }
}
