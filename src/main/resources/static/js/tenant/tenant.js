document.addEventListener("DOMContentLoaded", function() {
    // Ensure elements exist before adding event listeners
    const toggleSidebarButton = document.getElementById('toggle-sidebar');
    const logoutButton = document.getElementById('logout-btn');
    const sidebarLinks = document.querySelectorAll('#sidebar ul li a');

    if (toggleSidebarButton) {
        toggleSidebarButton.addEventListener('click', function() {
            document.getElementById('sidebar').classList.toggle('active');
            document.querySelector('main').classList.toggle('active');
        });
    }

    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            if (confirm("Are you sure you want to logout?")) {
                window.location.href = "/logout";
            }
        });
    }

    if (sidebarLinks) {
        sidebarLinks.forEach(link => {
            link.addEventListener('click', function(event) {
                event.preventDefault();
                let tab = this.getAttribute('href').substring(1);
                loadContent(tab);
            });
        });
    }
});

// Function to load content dynamically based on tab
function loadContent(tab) {
    // Hide all content sections
    let sections = document.querySelectorAll('.content-section');
    sections.forEach(section => {
        section.classList.remove('active');
    });

    // Show the selected section
    let selectedSection = document.getElementById(tab);
    if (selectedSection) {
        selectedSection.classList.add('active');
    }
}

// Add more JavaScript functionalities as needed
function deleteAccount() {
    if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
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

function redirectToHouse(houseId) {
    window.location.href = "/house/tenant-house/" + houseId;
}

function redirectToBuilding(buildingId) {
    window.location.href = "/building/tenants-building-view/" + buildingId;
}
