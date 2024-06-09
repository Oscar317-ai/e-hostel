document.addEventListener("DOMContentLoaded", function() {
    // Event listener for sidebar toggle button
    document.getElementById('toggle-sidebar').addEventListener('click', function() {
        document.getElementById('sidebar').classList.toggle('active');
        document.querySelector('main').classList.toggle('active');
    });

    // Event listener for logout button
    document.getElementById('logout-btn').addEventListener('click', function() {
        // Add logout functionality here
        alert("Logout functionality will be implemented here.");
    });

    // Load default content (My Profile) when the page loads
    loadContent('profile');

    // Event listeners for sidebar links
    document.querySelectorAll('#sidebar ul li a').forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            let tab = this.getAttribute('href').substring(1);
            loadContent(tab);
        });
    });
});

// Function to load content dynamically based on tab
function loadContent(tab) {
    document.querySelectorAll('section').forEach(section => {
        section.classList.add('hidden');
    });
    document.getElementById(tab).classList.remove('hidden');
}

// Add more JavaScript functionalities as needed
