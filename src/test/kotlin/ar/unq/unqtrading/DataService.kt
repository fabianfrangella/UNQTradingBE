package ar.unq.unqtrading

import ar.unq.unqtrading.dto.OrdenDeVentaDTO
import ar.unq.unqtrading.entities.Accion
import ar.unq.unqtrading.entities.Empresa
import ar.unq.unqtrading.entities.Persona
import ar.unq.unqtrading.repositories.AccionRepository
import ar.unq.unqtrading.repositories.EmpresaRepository
import ar.unq.unqtrading.repositories.OrdenDeVentaRepository
import ar.unq.unqtrading.repositories.PersonaRepository
import ar.unq.unqtrading.services.AccionService
import ar.unq.unqtrading.services.PersonaService
import ar.unq.unqtrading.services.interfaces.IEmpresaService
import ar.unq.unqtrading.services.interfaces.IOrdenDeVentaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DataService {
    @Autowired
    lateinit var ordenDeVentaService: IOrdenDeVentaService
    @Autowired
    lateinit var ordenDeVentaRepository: OrdenDeVentaRepository
    @Autowired
    lateinit var empresaService: IEmpresaService
    @Autowired
    lateinit var empresaRepository: EmpresaRepository
    @Autowired
    lateinit var personaService: PersonaService
    @Autowired
    lateinit var personaRepository: PersonaRepository
    @Autowired
    lateinit var accionRepository: AccionRepository

    fun crearDatos() {
        var orden1 = OrdenDeVentaDTO()
        var orden2 = OrdenDeVentaDTO()
        var empresa = Empresa()
        var coca = Empresa()
        var persona = Persona()
        var accionCoca = Accion()
        coca.nombreEmpresa = "Coca-Cola"
        coca.password = "12345"
        coca.email = "coca@coca.com"
        coca.cuit = 12345678999
        empresa.nombreEmpresa = "UNQ"
        empresa.password = "1234578"
        empresa.email = "unq@unq.com"
        empresa.cuit = 98765541111
        empresaService.save(empresa)
        empresaService.save(coca)
        persona.nombre = "Federico"
        persona.apellido = "Garetti"
        persona.username = "Fede"
        persona.password = "123456"
        persona.email = "fede@fede.com"
        persona.dni = 12345678
        persona.cuil = 20123456787
        accionCoca.empresa = coca
        accionCoca.cantidad = 1
        accionCoca.fechaUltimaCompra = LocalDate.now().plusDays(5)
        accionCoca.persona =persona
        persona.acciones.add(accionCoca)
        accionRepository.save(accionCoca)
        orden1.nombreEmpresa = "UNQ"
        orden2.nombreEmpresa = "UNQ"
        orden1.cantidadDeAcciones = 100
        orden2.cantidadDeAcciones = 200
        orden1.precio = 1000
        orden2.precio = 2000
        orden1.fechaDeVencimiento = LocalDate.of(2020,10,20)
        orden2.fechaDeVencimiento = LocalDate.of(2020,10,20)
        orden1.creadorId = coca.id
        orden2.creadorId = empresa.id
        ordenDeVentaService.saveOrdenDeVenta(orden1)
        ordenDeVentaService.saveOrdenDeVenta(orden2)
    }

    fun getIdEmpresaByNombre(nombre : String): Int? {
        return empresaRepository.findByNombreEmpresa(nombre)?.id
    }

    fun getIdPersonaByUsername(username : String): Int? {
        return personaRepository.findByUsername(username)?.id
    }

    fun eliminarDatos() {
        ordenDeVentaRepository.deleteAll()
        accionRepository.deleteAll()
        empresaRepository.deleteAll()
        personaRepository.deleteAll()
    }
}