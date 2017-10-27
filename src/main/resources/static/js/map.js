var geocoder;
var map;
/*gets address from opportunity object in thymeleaf*/
var address = document.getElementById("address").value;

/*creates map object*/
function initialize() {
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng(-1, 1);

    /*map options*/
    var myOptions = {
        zoom: 14,
        center: latlng,
        mapTypeControl: true,
        mapTypeControlOptions: {
        style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
    },
    navigationControl: true,
    mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("map"), myOptions);

    /*calls to Google API for address -> coordinates*/
    if (geocoder) {
        geocoder.geocode({
        'address': address
        }, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (status != google.maps.GeocoderStatus.ZERO_RESULTS) {
                    map.setCenter(results[0].geometry.location);

        /*Changes text on marker*/
        var infowindow = new google.maps.InfoWindow({
            content: '<b>' + address + '</b>',
            size: new google.maps.Size(150, 50)
        });

        /* Sets marker on map*/
        var marker = new google.maps.Marker({
            position: results[0].geometry.location,
            map: map,
            title: address
        });

        /*Listener for marker mouse click*/
        google.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map, marker);
        });

                } else {
                    alert("No results found");
                }
            } else {
                alert("Geocode was not successful for the following reason: " + status);
            }
        });
    }
}
/*Loads map when page loads*/
google.maps.event.addDomListener(window, 'load', initialize);
