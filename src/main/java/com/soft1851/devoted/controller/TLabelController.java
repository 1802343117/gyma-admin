package com.soft1851.devoted.controller;

import com.soft1851.devoted.domain.entity.TLabel;
import com.soft1851.devoted.service.TLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 12559
 */
@RestController
@RequestMapping("/tLabel")
@Slf4j
public class TLabelController {

    @Resource
    private TLabelService tLabelService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/selectLabel")
    public List selectLabel() {
        return tLabelService.selectLabel();
    }

    /**
     * 根据ID删除
     *
     * @param tbId
     */
    @DeleteMapping("/deleteById")
    void delete(Integer tbId) {
        tLabelService.deleteById(tbId);
    }

    /**
     * 添加留言标签
     *
     * @param tLabel
     */
    @PostMapping("/addLabel")
    void addLabel(TLabel tLabel) {
        tLabelService.addLabel(tLabel);
    }

    /**
     * 修改留言标签
     *
     * @param tLabel
     */
    @PutMapping("/updateById")
    void updateLabel(TLabel tLabel) {
        tLabelService.updateById(tLabel);
    }
}
