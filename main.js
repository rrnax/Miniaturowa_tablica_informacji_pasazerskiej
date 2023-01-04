// Inicialization about updates section
const meetings = document.querySelectorAll(".update-item");
const details = document.querySelectorAll(".update-content");
let eriler = meetings.length-1;
details[meetings.length-1].style.display = 'block';

//Animation varibles
const train = document.getElementById("train");
const site = document.querySelector("#animation");
const letters = document.querySelectorAll("h1");
var letter_number, letters_sum_width;

//Tracks varibles
const timeline = document.querySelector(".timeline")
const one_wood = document.querySelector("#one_wood");
var last_tracks = Math.round(((timeline.offsetHeight - 128) / one_wood.offsetHeight)/2) - 1;

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

//Wood to tracks
for (let j = 0, copy; j < last_tracks; j++) {
    copy = one_wood.cloneNode(true);
    one_wood.parentNode.insertBefore(copy, one_wood);
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

//Animation
function animate() {
    letter_number = 0;
    letters_sum_width = 0;
    let stop_interval = null;
    let road_length = site.offsetWidth;
    let position = 0 - train.offsetWidth;

    clearInterval(stop_interval);
    stop_interval = setInterval(travel, 10);

    function travel() {
        if (position > road_length) {

            clearInterval(stop_interval);
            setTimeout(() => { site.style.display = "none"; }, 1000);
        } else {

            if(0.07 * road_length
                + letters[letter_number].offsetWidth
                + letters_sum_width
                <= position){

                letters[letter_number].style.display = "inline";
                letters_sum_width += letters[letter_number].offsetWidth;
                if(letter_number < letters.length - 1){
                    letter_number++;

                }
            }

            position+= 9;
            train.style.left = position + "px";

        }
    }
}

window.addEventListener("DOMContentLoaded", animate, false);
