document.addEventListener('DOMContentLoaded', function() {
    // Display the first tab content by default
    document.getElementById("tasks").style.display = "block";



    // Other data initialization code can be added here
});

function openSection(evt, sectionName) {
    // Hide all tab contents
    const tabcontents = document.getElementsByClassName("tabcontent");
    for (let i = 0; i < tabcontents.length; i++) {
        tabcontents[i].style.display = "none";
    }

    // Remove the background color of all tablinks
    const tablinks = document.getElementsByClassName("tablink");
    for (let i = 0; i < tablinks.length; i++) {
        tablinks[i].style.backgroundColor = "";
    }

    // Show the current tab content and add an "active" class to the clicked button
    document.getElementById(sectionName).style.display = "block";
    evt.currentTarget.style.backgroundColor = "#e14e50";
}

// tenant.js

function confirmLogout() {
    if (confirm("Are you sure you want to logout?")) {
        // Redirect to logout URL
        window.location.href = "/logout";
    }
}

function deleteAccount() {
    if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
        // Call the delete account URL
        fetch('/delete-account', { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    alert("Account deleted successfully.");
                    window.location.href = "/logout";
                } else {
                    alert("Failed to delete account.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("An error occurred while deleting your account.");
            });
    }
}
function redirectToBuilding(buildingId) {
  window.location.href = "/building/landlords-building-view/" + buildingId;

}
