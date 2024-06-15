// building.js


function initMap() {
        var mapOptions = {
            center: { lat: -34.397, lng: 150.644 },
            zoom: 8
        };
        var map = new google.maps.Map(document.getElementById('map'), mapOptions);
    }

    $(document).ready(function() {
        $('#edit-button').click(function() {
            $('#editBuildingForm').toggle();
        });
    });

function redirectToHouse(houseId) {
  window.location.href = "/building/landlords-house-view/" + houseId;
}




