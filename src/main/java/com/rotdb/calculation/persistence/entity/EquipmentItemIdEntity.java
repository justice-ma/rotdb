package com.rotdb.calculation.persistence.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="equipment_item_id")
public class EquipmentItemIdEntity {
    @Id
    @Column(name="item_id")
    private Integer id;

    @Column(name="equipment_id")
    private Long equipmentId;

    @Column(name="wiki_page")
    private String wikiPage;

    @Column(name="version_anchor")
    private String versionAnchor;

    @Column(name="item_name")
    private String itemName;
    private String source;

    @Column(name="last_seen_at")
    private Date lastSeenAt;

    public Integer getId() {
        return id;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public String getWikiPage() {
        return wikiPage;
    }

    public String getVersionAnchor() {
        return versionAnchor;
    }

    public String getItemName() {
        return itemName;
    }

    public String getSource() {
        return source;
    }

    public Date getLastSeenAt() {
        return lastSeenAt;
    }
}

