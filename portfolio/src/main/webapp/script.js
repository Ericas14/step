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


function getFromServlet(){
    fetch('/data').then(response => response.text()).then((comment) => { 

    const commentContainer = document.getElementById('comment-container');
    console.assert(commentContainer, "You have no element with ID 'comment-container'"); 
    
    
    const list = document.getElementById('comment-container');
    comment = comment.replace("[", "");
    comment = comment.replace("]","");
    commArr = comment.split(',');
    for(i =0;  i < commArr.length; i++){
        list.appendChild(createListElement(commArr[i]));
    }
    });
    
   
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}


