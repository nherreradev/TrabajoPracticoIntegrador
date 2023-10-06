package com.unlam.tpi.controlador;

import com.unlam.tpi.servicio.listaPreciosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("list")
public class PriceListControllerImpl implements PriceListController {

    @Autowired
    private listaPreciosServicio priceListService;

    @Override
    @GetMapping("/precios/{titulo}")
    //El titulo puede ser accion o bono (
    public ResponseEntity<String> MostrarPrecios(@PathVariable String titulo) {
        String token = "4kkefDDGgMGaRHydvvycRdr1BnDjpxr-AlDwzVhUn8czAtVpGW2i73PKVMORlw_gw3alWJBMbUusYNNAjzUL1QLrF4curuQiR3zL0xKfLBgYUkJAkVO2z9yl1kPu-vrn1icCuw3bJd1noiTVk6X57b26Z-u3n2swx2T-dkXT-UTi6L_g-y0fb6lxw4GBdq0tDLejsnFLsCfGPqxtwKi1U1cNM9W1dBXIwBQSEUq0l_DXYOBAqAmRQGWmZXTz3ail3ywMEdH0D8grzPnEZvx0n8fIK-5eW6PIK6MofIQcyZdtPMP8i4LuQnd0Lso_UsG5Glq-UlYaAaSjEaDj0AVLLwQgVzcr7eO9PaBA5r94bvCqNMMLmve8JvI-sZu35JJtyPFK1mIeky6mgcQSN4A01Uej7lJqP8AkHmdy_pQMaEo";
        ResponseEntity<String> res = priceListService.GetPriceList(titulo, token);
        if (res == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("TOKEN TIME OUT");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Transacción guardada con éxito, se guardaron: " + titulo);
    }
}
