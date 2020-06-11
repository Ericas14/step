// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

//Global variable for later use of markers
var map;
var markerList = [];

/**
 * Adds a random fact to the page.
 */
function addRandomFact() {
  const facts =
      ['I like to knit!', 'I am an avid reader', 'My favorite programming language is Java', 'I am from Ashland, VA'];

  // Pick a random fact.
  const randomFact = facts[Math.floor(Math.random() * facts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = randomFact;
}


function generateComments(value){

    fetch('/data?load-comments='+value).then(response => response.json()).then((comments) => {
      const commentContainer = document.getElementById('comment-container');
      console.assert(commentContainer, "You have no element with ID 'comment-container'");
      document.getElementById("comment-container").innerHTML=""; 
      for(i =0;  i < comments.length; i++){
          commentContainer.appendChild(createListElement(comments[i]));
        }
    });
    
   
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

/*Tells the server to delete the comments through getPost and then clears
deleted comments from page */
function deleteComments(){
  const params = new URLSearchParams();
  fetch('/delete-data', {method: 'POST', body: params});
  generateComments(0);
}

function dayMode() {
  map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 37.7590, lng: -77.4800}, zoom: 7});
}

function nightMode(){
    // Styles a map in night mode.
        map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 37.7590, lng: -77.4800},
          zoom: 7,
          styles: [
            {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
            {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
            {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
            {
              featureType: 'administrative.locality',
              elementType: 'labels.text.fill',
              stylers: [{color: '#d59563'}]
            },
            {
              featureType: 'poi',
              elementType: 'labels.text.fill',
              stylers: [{color: '#d59563'}]
            },
            {
              featureType: 'poi.park',
              elementType: 'geometry',
              stylers: [{color: '#263c3f'}]
            },
            {
              featureType: 'poi.park',
              elementType: 'labels.text.fill',
              stylers: [{color: '#6b9a76'}]
            },
            {
              featureType: 'road',
              elementType: 'geometry',
              stylers: [{color: '#38414e'}]
            },
            {
              featureType: 'road',
              elementType: 'geometry.stroke',
              stylers: [{color: '#212a37'}]
            },
            {
              featureType: 'road',
              elementType: 'labels.text.fill',
              stylers: [{color: '#9ca5b3'}]
            },
            {
              featureType: 'road.highway',
              elementType: 'geometry',
              stylers: [{color: '#746855'}]
            },
            {
              featureType: 'road.highway',
              elementType: 'geometry.stroke',
              stylers: [{color: '#1f2835'}]
            },
            {
              featureType: 'road.highway',
              elementType: 'labels.text.fill',
              stylers: [{color: '#f3d19c'}]
            },
            {
              featureType: 'transit',
              elementType: 'geometry',
              stylers: [{color: '#2f3948'}]
            },
            {
              featureType: 'transit.station',
              elementType: 'labels.text.fill',
              stylers: [{color: '#d59563'}]
            },
            {
              featureType: 'water',
              elementType: 'geometry',
              stylers: [{color: '#17263c'}]
            },
            {
              featureType: 'water',
              elementType: 'labels.text.fill',
              stylers: [{color: '#515c6d'}]
            },
            {
              featureType: 'water',
              elementType: 'labels.text.stroke',
              stylers: [{color: '#17263c'}]
            }
          ]
        });
}

function markers(){
   const homeTown = new google.maps.Marker({
    position: {lat: 37.7590, lng: -77.4800},
    map: map,
    title: 'My Hometown'
  });
  markerList.push(homeTown);

   const college = new google.maps.Marker({
    position: {lat: 36.072639, lng: -79.771370},
    map: map,
    title: 'The university I attend'
  });
  markerList.push(college);

  const yarnStore = new google.maps.Marker({
    position: {lat: 37.759030, lng: -77.481750},
    map: map,
    title: 'My favorite yarn store'
  });
  markerList.push(yarnStore);

  const beach = new google.maps.Marker({
    position: {lat: 37.268181, lng: -76.010834},
    map: map,
    title: 'My favorite beach'
  });
  markerList.push(beach);

  const library = new google.maps.Marker({
    position: {lat: 37.590190, lng: -77.496580},
    map: map,
    title: 'My favorite library'
  });
  markerList.push(library);
}

function removeMarkers(){
    for(i =0; i < markerList.length; i++){
        markerList[i].setMap(null);
    }
}



