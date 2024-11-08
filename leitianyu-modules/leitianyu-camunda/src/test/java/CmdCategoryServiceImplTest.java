//import com.jijia.camunda.domain.CmdCategory;
//import com.jijia.camunda.domain.vo.CmdCategoryVo;
//import com.jijia.camunda.mapper.CmdCategoryMapper;
//import com.jijia.camunda.service.impl.CmdCategoryServiceImpl;
//import com.jijia.common.core.exception.GlobalException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CmdCategoryServiceImplTest {
//
//    @Mock
//    private CmdCategoryMapper cmdCategoryMapper;
//
//    @InjectMocks
//    private CmdCategoryServiceImpl cmdCategoryService;
//
//    private CmdCategoryVo cmdCategoryVo;
//    private CmdCategory cmdCategory;
//
//    @BeforeEach
//    public void setUp() {
//        cmdCategoryVo = new CmdCategoryVo();
//        cmdCategoryVo.setName("Test Category");
//        cmdCategoryVo.setCode("TEST_CODE");
//        cmdCategoryVo.setDescription("TEST_CODE");
//
//        cmdCategory = new CmdCategory();
//        cmdCategory.setCategoryId(1L);
//        cmdCategory.setName("Test Category");
//        cmdCategory.setCode("TEST_CODE");
//    }
//
//    @Test
//    public void testCreateCategory() {
//
//
//        Long categoryId = cmdCategoryService.createCategory(cmdCategoryVo);
//
//        assertNotNull(categoryId);
//        verify(cmdCategoryMapper, times(1)).insert(any(CmdCategory.class));
//    }
//
//    @Test
//    public void testUpdateCategory() {
//        when(cmdCategoryMapper.selectById(anyLong())).thenReturn(cmdCategory);
//        when(cmdCategoryMapper.selectOne(any())).thenReturn(null);
//        doAnswer(invocation -> null).when(cmdCategoryMapper).updateById(any());
//
//        assertDoesNotThrow(() -> cmdCategoryService.updateCategory(cmdCategoryVo));
//        verify(cmdCategoryMapper, times(1)).updateById(any(CmdCategory.class));
//    }
//
//    @Test
//    public void testDeleteCategory() {
//        when(cmdCategoryMapper.selectById(anyLong())).thenReturn(cmdCategory);
//        doNothing().when(cmdCategoryMapper).deleteById(anyLong());
//
//        assertDoesNotThrow(() -> cmdCategoryService.deleteCategory(1L));
//        verify(cmdCategoryMapper, times(1)).deleteById(1L);
//    }
//
//    @Test
//    public void testGetCategory() {
//        when(cmdCategoryMapper.selectCmdCategoryByCategoryId(anyLong())).thenReturn(cmdCategory);
//
//        CmdCategory result = cmdCategoryService.getCategory(1L);
//
//        assertNotNull(result);
//        assertEquals(cmdCategory.getCategoryId(), result.getCategoryId());
//    }
//
//    @Test
//    public void testGetCategoryPage() {
//        when(cmdCategoryMapper.selectCmdCategoryList(any())).thenReturn(Collections.singletonList(cmdCategory));
//
//        List<CmdCategory> result = cmdCategoryService.getCategoryPage(cmdCategoryVo);
//
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//    }
//
//    @Test
//    public void testValidateCategoryNameUnique() {
//        when(cmdCategoryMapper.selectOne(any())).thenReturn(null);
//
//        assertDoesNotThrow(() -> cmdCategoryService.validateCategoryNameUnique(cmdCategoryVo));
//    }
//
//    @Test
//    public void testValidateCategoryCodeUnique() {
//        when(cmdCategoryMapper.selectOne(any())).thenReturn(null);
//
//        assertDoesNotThrow(() -> cmdCategoryService.validateCategoryCodeUnique(cmdCategoryVo));
//    }
//
//    @Test
//    public void testValidateCategoryExists() {
//        when(cmdCategoryMapper.selectById(anyLong())).thenReturn(cmdCategory);
//
//        assertDoesNotThrow(() -> cmdCategoryService.validateCategoryExists(1L));
//    }
//}