package sfera.order_service.order;


import org.springframework.data.jpa.repository.JpaRepository;
import sfera.order_service.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
