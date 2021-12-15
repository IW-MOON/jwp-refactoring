package kitchenpos.ui;

import kitchenpos.application.TableService;
import kitchenpos.domain.OrderTable;
import kitchenpos.dto.OrderTableRequest;
import kitchenpos.dto.OrderTableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TableRestController {
    private final TableService tableService;

    public TableRestController(final TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/api/tables")
    public ResponseEntity<OrderTableResponse> create(@RequestBody final OrderTableRequest orderTableRequest) {
        final OrderTable created = tableService.create(orderTableRequest);
        final URI uri = URI.create("/api/tables/" + created.getId());
        return ResponseEntity.created(uri)
                .body(OrderTableResponse.from(created))
                ;
    }

    @GetMapping("/api/tables")
    public ResponseEntity<List<OrderTableResponse>> list() {

        List<OrderTable> orderTables = tableService.list();
        List<OrderTableResponse> orderTableResponses = orderTables.stream()
                                                                .map(OrderTableResponse::from)
                                                                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(orderTableResponses)
                ;
    }

    @PutMapping("/api/tables/{orderTableId}/empty")
    public ResponseEntity<OrderTableResponse> changeEmpty(
            @PathVariable final Long orderTableId,
            @RequestBody final OrderTableRequest orderTableRequest
    ) {
        OrderTableResponse orderTableResponse = OrderTableResponse.from(tableService.changeEmpty(orderTableId, orderTableRequest));
        return ResponseEntity.ok()
                .body(orderTableResponse)
                ;
    }

    @PutMapping("/api/tables/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableResponse> changeNumberOfGuests(
            @PathVariable final Long orderTableId,
            @RequestBody final OrderTableRequest orderTableRequest
    ) {
        OrderTableResponse orderTableResponse = OrderTableResponse.from(tableService.changeNumberOfGuests(orderTableId, orderTableRequest));
        return ResponseEntity.ok()
                .body(orderTableResponse)
                ;
    }
}
