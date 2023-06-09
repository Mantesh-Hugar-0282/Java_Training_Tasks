package rgt_training_task3;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentalService implements RentalCostCalculator {
    private List<Vehicle> vehicles;
    private List<Rental> rentals;

    public RentalService() {
        this.vehicles = new ArrayList<>();
        this.rentals = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    public Rental rentVehicle(Customer customer, Vehicle vehicle, LocalDateTime startTime, LocalDateTime endTime) {
        if (!vehicle.isAvailable()) {
        	System.out.println();
            return null; // Vehicle is not available for rent
        }
        Rental rental = new Rental(vehicle, customer, startTime, endTime);
        vehicle.setAvailable(false);
        rentals.add(rental);
        return rental;
    }

    @Override
    public BigDecimal calculateRentalCost(Rental rental) {
        // Implementation of rental cost calculation based on duration
        // You can customize this calculation based on your specific requirements
        long durationInHours = rental.getStartTime().until(rental.getEndTime(), java.time.temporal.ChronoUnit.HOURS);
        BigDecimal hourlyRate = new BigDecimal("100.00"); // Example hourly rate
        return hourlyRate.multiply(BigDecimal.valueOf(durationInHours));
    }

    public boolean returnVehicle(Rental rental) {
        if (!rentals.contains(rental)) {
            return false; // Rental not found
        }
        rental.getRentedVehicle().setAvailable(true);
        rentals.remove(rental);
        return true;
    }

}
