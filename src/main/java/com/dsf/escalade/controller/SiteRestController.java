package com.dsf.escalade.controller;

import com.dsf.escalade.service.business.SiteService;
import com.dsf.escalade.web.dto.SiteDto;
import com.dsf.escalade.web.dto.SiteFullDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SiteRestController {

   private final SiteService siteService;

   @Autowired
   public SiteRestController(SiteService siteService) {

      this.siteService = siteService;
   }


   @GetMapping("/site/{siteId}")
   public ResponseEntity<SiteDto> getSite(@PathVariable("siteId") Integer siteId) {

      try(SiteDto siteDto = siteService.getOne(siteId)) {
         log.info("GET:/site/" + siteId);
         return ResponseEntity.ok(siteDto);
      } catch (Exception e) {
         return ResponseEntity.notFound().build();
      }
   }

   @GetMapping("/site/full/{siteId}")
   public ResponseEntity<SiteFullDto> getSiteFull(@PathVariable("siteId") Integer siteId) {

      try (SiteFullDto siteFullDto = siteService.getFull(siteId)) {
         log.info("GET:/site/full" + siteId);
         return ResponseEntity.ok(siteFullDto);
      } catch (Exception e) {
         return ResponseEntity.notFound().build();
      }
   }


}


