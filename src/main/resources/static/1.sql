CREATE TABLE config_info (
  id bigint NOT NULL generated by default as identity,
  data_id varchar(255) NOT NULL,
  group_id varchar(128) NOT NULL,
  tenant_id varchar(128) default '',
  app_name varchar(128),
  content LONG VARCHAR NOT NULL,
  constraint configinfo_id_key PRIMARY KEY (id),
  constraint uk_configinfo_datagrouptenant UNIQUE (data_id,group_id,tenant_id));

CREATE INDEX configinfo_dataid_key_idx ON config_info(data_id);
CREATE INDEX configinfo_groupid_key_idx ON config_info(group_id);
CREATE INDEX configinfo_dataid_group_key_idx ON config_info(data_id, group_id);