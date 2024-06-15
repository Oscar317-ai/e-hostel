function initMap() {
    var mapOptions = {
        center: { lat: -34.397, lng: 150.644 },
        zoom: 8
    };
    var map = new google.maps.Map(document.getElementById('map'), mapOptions);
}

function toggleEditForm() {
    var form = document.getElementById('editBuildingForm');
    if (form.style.display === 'none' || form.style.display === '') {
        form.style.display = 'block';
    } else {
        form.style.display = 'none';
    }
}

function redirectToHouse(houseId) {
    window.location.href = "/building/landlords-house-view/" + houseId;
}
