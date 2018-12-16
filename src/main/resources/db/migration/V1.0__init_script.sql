CREATE TABLE public.user_accounts
(
  id                         bigserial PRIMARY KEY         NOT NULL,
  creation_time              timestamp without time zone   NOT NULL,
  email                      character varying(100) UNIQUE NOT NULL,
  first_name                 character varying(100)        NOT NULL,
  last_name                  character varying(100)        NOT NULL,
  modification_time          timestamp without time zone   NOT NULL,
  password                   character varying(255),
  role                       character varying(20)         NOT NULL,
  sign_in_provider           character varying(20),
  version                    bigint                        NOT NULL
);

create table oauth_client_details (
  client_id               VARCHAR(256) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
);

create table oauth_client_token (
  token_id          VARCHAR(256),
  token             BYTEA,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256)
);

create table oauth_access_token (
  token_id          VARCHAR(256),
  token             BYTEA,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication    BYTEA,
  refresh_token     VARCHAR(256)
);

create table oauth_refresh_token (
  token_id       VARCHAR(256),
  token          BYTEA,
  authentication BYTEA
);

create table oauth_code (
  code           VARCHAR(256),
  authentication BYTEA
);

create table oauth_approvals (
  userId         VARCHAR(256),
  clientId       VARCHAR(256),
  scope          VARCHAR(256),
  status         VARCHAR(10),
  expiresAt      TIMESTAMP,
  lastModifiedAt TIMESTAMP
);

-- customized oauth_client_details table
create table ClientDetails (
  appId                  VARCHAR(256) PRIMARY KEY,
  resourceIds            VARCHAR(256),
  appSecret              VARCHAR(256),
  scope                  VARCHAR(256),
  grantTypes             VARCHAR(256),
  redirectUrl            VARCHAR(256),
  authorities            VARCHAR(256),
  access_token_validity  INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation  VARCHAR(4096),
  autoApproveScopes      VARCHAR(256)
);

ALTER TABLE public.user_accounts
  ADD is_account_non_locked boolean DEFAULT true  NOT NULL;
ALTER TABLE public.user_accounts
  ADD is_account_non_expired boolean DEFAULT true  NOT NULL;
ALTER TABLE public.user_accounts
  ADD is_credentials_non_expired boolean DEFAULT true NOT NULL;
ALTER TABLE public.user_accounts
  ADD is_enabled boolean DEFAULT false  NOT NULL;
ALTER TABLE public.user_accounts
  ADD verification_code bigint NULL;
CREATE UNIQUE INDEX user_accounts_verification_code_uindex
  ON public.user_accounts (verification_code);



INSERT INTO public.user_accounts (id, creation_time, email, first_name, last_name, modification_time, password, role, sign_in_provider, version, is_account_non_locked, is_account_non_expired, is_credentials_non_expired, is_enabled, verification_code)
VALUES
  (21, '2018-09-26 23:40:26.502000', 'derevets.artem@hotmail.com', 'Artem', 'Derevets', '2018-09-26 23:40:43.817000',
       '$2a$10$Di.wn9XO3UJ6XIQn6D/R.ep0f8UXuZOM6fI6uvMgRsR2GrmBFDXaq', 'ROLE_USER', null, 1, true, true, true, true,
   null);
INSERT INTO public.user_accounts (id, creation_time, email, first_name, last_name, modification_time, password, role, sign_in_provider, version, is_account_non_locked, is_account_non_expired, is_credentials_non_expired, is_enabled, verification_code)
VALUES (79, '2018-10-15 03:06:40.286000', 'derevets.artem@gmail.com', 'artem', 'derevets', '2018-10-15 03:06:56.643000',
            '$2a$10$57ilfsdEiVrSE25BqMqeye2mEH5v8NGRZq/QJaW4nFJFtYQkDQX.m', 'ROLE_USER', null, 1, true, true, true,
        true, null);

INSERT INTO public.oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('spring-security-oauth2-read-write-client', 'resource-server-rest-api',
  '$2a$10$ZVIh8SIneg7K52EDwUKKfeybFHvjqNQEoQ7Z/eNlMwGdpJy4bLBYK', 'read,write',
  'password,authorization_code,refresh_token', '', 'ROLE_USER', 12500, 36000, null, null);





