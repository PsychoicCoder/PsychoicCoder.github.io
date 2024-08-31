var myHeading = document.querySelector("h1");
const url = 'https://img.shields.io/badge/any_text-you_like-blue?style=for-the-badge';
const response = await fetch(url);
const text = await response.text();
console.log(text);
myHeading.textContent = text;
