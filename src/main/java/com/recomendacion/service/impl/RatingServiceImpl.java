package com.recomendacion.service.impl;

import com.recomendacion.dto.RatingDTO;
import com.recomendacion.exception.NotFoundException;
import com.recomendacion.model.Rating;
import com.recomendacion.model.Producto;
import com.recomendacion.registro.model.Usuario;
import com.recomendacion.repository.ProductoRepository;
import com.recomendacion.repository.RatingRepository;
import com.recomendacion.registro.repository.UsuarioRepository;
import com.recomendacion.service.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public RatingServiceImpl(RatingRepository ratingRepository,
                             UsuarioRepository usuarioRepository,
                             ProductoRepository productoRepository) {
        this.ratingRepository = ratingRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingDTO> obtenerTodos() {
        return ratingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingDTO> obtenerPorUsuario(Long usuarioId) {
        return ratingRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingDTO> obtenerPorProducto(Long productoId) {
        return ratingRepository.findByProductoId(productoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RatingDTO crear(RatingDTO ratingDTO) {
        Usuario usuario = usuarioRepository.findById(ratingDTO.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + ratingDTO.getUsuarioId()));

        Producto producto = productoRepository.findById(ratingDTO.getProductoId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + ratingDTO.getProductoId()));

        Rating rating = new Rating();
        rating.setUsuario(usuario);
        rating.setProducto(producto);
        rating.setValoracion(ratingDTO.getValoracion());
        rating.setComentario(ratingDTO.getComentario());
        rating.setFecha(LocalDateTime.now());

        Rating ratingGuardado = ratingRepository.save(rating);
        return convertToDTO(ratingGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RatingDTO> obtenerPorId(Long id) {
        return ratingRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public RatingDTO actualizar(Long id, RatingDTO ratingDTO) {
        Rating ratingExistente = ratingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rating no encontrado con ID: " + id));

        // Actualizar solo campos modificables
        ratingExistente.setValoracion(ratingDTO.getValoracion());
        ratingExistente.setComentario(ratingDTO.getComentario());

        Rating ratingActualizado = ratingRepository.save(ratingExistente);
        return convertToDTO(ratingActualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new NotFoundException("Rating no encontrado con ID: " + id);
        }
        ratingRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obtenerPromedioPorProducto(Long productoId) {
        Double promedio = ratingRepository.calcularPromedioRatingPorProducto(productoId);
        return promedio != null ?
                BigDecimal.valueOf(promedio).setScale(1, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeRatingDeUsuarioParaProducto(Long usuarioId, Long productoId) {
        return ratingRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId);
    }

    private RatingDTO convertToDTO(Rating rating) {
        return RatingDTO.builder()
                .id(rating.getId())
                .usuarioId(rating.getUsuario().getId())
                .productoId(rating.getProducto().getId())
                .valoracion(rating.getValoracion())
                .comentario(rating.getComentario())
                .fecha(rating.getFecha())
                .build();
    }
}