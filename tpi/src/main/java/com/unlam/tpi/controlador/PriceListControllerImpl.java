package com.unlam.tpi.controlador;

import com.unlam.tpi.servicio.listaPreciosServicio;
import org.springframework.beans.factory.annotation.Autowired;
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
        String token = "PRW0SBME1R42f9sMBGwoRBlMGUWvbWXKpGSnVWdjddricWZ50HqlWb4jvFgsVIyAIdlokg30Bz2aNpNFRfNu_CKK_yLYMP3kBTKXY6DT3_539EKo5Nz5L-p4k98q8vMm9BwcA0EYWAaDhNebmbjueSm9mwnmDz8YC5kf7p02Vjuihxz63U2lIk0ESwqQ2Lyyb78DYJ_Z5nwT9gykge0xGfgS87tEEO8Ws2cGYWZ5xvon9esubII2JxFum5Xl7ubO-RR8BdNV4718t4fRMo-JTRjWk-a4qBlwWR_bv4UNsa12aSJI8pSbxnDNBaB8KzPLtXgQFiKyJcWU0A-Du9cYStFYlJJVwCPdZ1-Qe-ywI_b4mXsClI-9iNmN8UqQF9ymoASKG79fmtGQ_PsM-78sZH6c_IYqJausqu4yIjdBdCU";
        ResponseEntity<String> res = priceListService.GetPriceList(titulo, token);
        return res;
    }
}
