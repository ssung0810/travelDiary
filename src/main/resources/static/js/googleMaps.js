var script = document.createElement('script');
script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyD-XMHW8c1Sp58WrBxnyz7M-ZtZpDarZUc&callback=initMap';
script.async = true;

// Attach your callback function to the `window` object
var map;
const countries = {
        kr: {
          center : { lat: 37.5642135 ,lng: 127.0016985 },
          zoom: 16,
        }

}
window.initMap = function() {
  // JS API is loaded and available
  map = new google.maps.Map(document.getElementById("map"), {
            zoom: countries["kr"].zoom,
            center: countries["kr"].center,
            mapTypeControl: true,
            panControl: true,
            zoomControl: true,
            streetViewControl: true,
          });

//  map = new google.maps.Map(document.getElementById('map'), {
//    center: {lat: -34.397, lng: 150.644},
//    zoom: 8
//  });
};

// Append the 'script' element to 'head'
document.head.appendChild(script);
