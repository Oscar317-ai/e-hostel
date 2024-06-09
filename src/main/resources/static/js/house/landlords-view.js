function submitForm(event) {
    event.preventDefault();
    if (!document.getElementById("termsCheckbox").checked) {
        alert("Please accept the terms and conditions.");
        return;
    }
    // Validate other form inputs if needed
    let idNumber = document.getElementById("idNumber").value;
    let phoneNumber = document.getElementById("phoneNumber").value;
    if (!idNumber || !phoneNumber) {
        alert("Please fill in all required fields.");
        return;
    }
    // Submit form data via AJAX or proceed with a regular form submission
    // For demonstration purposes, let's assume a successful AJAX request
    simulatePaymentProcess();
}

function simulatePaymentProcess() {
    // Show loading animation while waiting for response
    showLoadingAnimation();
    // Simulate payment process with a delay
    setTimeout(() => {
        // Once payment is successful, update house status and add to tenant's homes
        updateHouseStatusAndAddToTenantHomes();
    }, 4000);
}

function showLoadingAnimation() {
    // Display a loading animation (e.g., spinner) to indicate processing
    // For demonstration purposes, let's log a message instead
    console.log("Processing payment...");
}

function updateHouseStatusAndAddToTenantHomes() {
    // Perform AJAX request to update house status and add to tenant's homes
    // For demonstration purposes, let's log a success message
    console.log("Payment successful. Updating house status and adding to tenant's homes...");
}
