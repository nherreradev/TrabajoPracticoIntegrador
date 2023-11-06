package com.unlam.tpi.delivery.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unlam.tpi.core.interfaces.PosicionServicio;
import com.unlam.tpi.core.interfaces.RendimientoControlador;
import com.unlam.tpi.core.modelo.RequestPorcentaje;
import com.unlam.tpi.core.modelo.ResponsePorcentaje;

@CrossOrigin
@RestController
@RequestMapping("/rendimiento")
public class RendimientoControladorImpl implements RendimientoControlador {

	@Autowired
	PosicionServicio posicionServicio;

	@Override
	@PostMapping("/calcular/porcentaje")
	public ResponseEntity<Map<String, ResponsePorcentaje>> calcularPorcentajeGananciaPerdidaDeTodosLosInstrumentosEnCartera(
			@RequestBody RequestPorcentaje request) {
		Map<String, ResponsePorcentaje> responsePorcentaje = posicionServicio
				.calcularPorcentajeGananciaPerdida(request.getToken());
		return ResponseEntity.ok(responsePorcentaje);
	}

}
