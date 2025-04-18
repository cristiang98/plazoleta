package com.traceability.infrastructure.out.jpa.adapter;

import com.traceability.domain.model.TraceabilityModel;
import com.traceability.infrastructure.out.jpa.entity.TraceabilityEntity;
import com.traceability.infrastructure.out.jpa.mapper.ITraceabilityEntityMapper;
import com.traceability.infrastructure.out.jpa.repository.ITraceabilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TraceabilityJpaAdapterTest {

    @InjectMocks
    private TraceabilityJpaAdapter traceabilityJpaAdapter;

    @Mock
    private ITraceabilityRepository traceabilityRepository;

    @Mock
    private ITraceabilityEntityMapper traceabilityEntityMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTraceability() {
        // GIVEN
        TraceabilityModel traceabilityModel = new TraceabilityModel(); // Crea tu modelo con los datos requeridos
        TraceabilityEntity traceabilityEntity = new TraceabilityEntity(); // Crea la entidad que el mapper debe devolver

        // Configuración de mocks
        when(traceabilityEntityMapper.toEntity(traceabilityModel)).thenReturn(traceabilityEntity);

        // WHEN
        traceabilityJpaAdapter.saveTraceability(traceabilityModel);

        // THEN
        verify(traceabilityEntityMapper, times(1)).toEntity(traceabilityModel);
        verify(traceabilityRepository, times(1)).save(traceabilityEntity);
    }

    @Test
    void testFindTraceabilityByIdClient() {
        // GIVEN
        Integer idClient = 1; // Identificador para buscar
        TraceabilityEntity traceabilityEntity = new TraceabilityEntity(); // Mock de la entidad
        TraceabilityModel expectedTraceabilityModel = new TraceabilityModel(); // Mock del modelo esperado

        // Configuración de mocks
        when(traceabilityRepository.findByIdClient(idClient)).thenReturn(traceabilityEntity);
        when(traceabilityEntityMapper.toModel(traceabilityEntity)).thenReturn(expectedTraceabilityModel);

        // WHEN
        TraceabilityModel result = traceabilityJpaAdapter.findTraceabilityByIdClient(idClient);

        // THEN
        verify(traceabilityRepository, times(1)).findByIdClient(idClient);
        verify(traceabilityEntityMapper, times(1)).toModel(traceabilityEntity);
        assertEquals(expectedTraceabilityModel, result);
    }

    @Test
    void testFindTraceabilityByIdOrderAndStatus() {
        // GIVEN
        Integer idOrder = 123; // Ejemplo de id de orden
        String status = "COMPLETED"; // Ejemplo de estado
        TraceabilityEntity traceabilityEntity = new TraceabilityEntity(); // Entidad simulada
        TraceabilityModel expectedTraceabilityModel = new TraceabilityModel(); // Resultado esperado

        // Configuración de mocks
        when(traceabilityRepository.findByIdOrderAndStatusCurrent(idOrder, status)).thenReturn(traceabilityEntity);
        when(traceabilityEntityMapper.toModel(traceabilityEntity)).thenReturn(expectedTraceabilityModel);

        // WHEN
        TraceabilityModel result = traceabilityJpaAdapter.findTraceabilityByIdOrderAndStatus(idOrder, status);

        // THEN
        verify(traceabilityRepository, times(1)).findByIdOrderAndStatusCurrent(idOrder, status);
        verify(traceabilityEntityMapper, times(1)).toModel(traceabilityEntity);
        assertEquals(expectedTraceabilityModel, result);
    }


}