package org.alexdev.havana.dao.mysql;

import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.catalogue.CatalogueItem;
import org.alexdev.havana.game.misc.purse.Voucher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoucherDao {
    public record VoucherAdmin(String voucherCode, int credits, String expiryDate, boolean singleUse, boolean allowNewUsers) {}
    public record VoucherItemAdmin(String voucherCode, String catalogueSaleCode) {}
    public record VoucherHistoryAdmin(String voucherCode, int userId, String usedAt, Integer creditsRedeemed, String itemsRedeemed) {}

    /**
     * Redeems a voucher.
     * Gets the voucher and then deletes it from the voucher table.
     *
     * @param voucherCode the string voucher code to redeem
     * @return the amount of credits redeemed or -1 if no voucher was found.
     */
    public static Voucher redeemVoucher(String voucherCode, int userId) {
        Voucher voucher = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            //Get the voucher
            preparedStatement = Storage.getStorage().prepare("SELECT credits,is_single_use,allow_new_users FROM vouchers WHERE voucher_code = ? " +
                    "AND (expiry_date IS NULL OR (UNIX_TIMESTAMP() < UNIX_TIMESTAMP(expiry_date))) AND " +
                    "NOT EXISTS (SELECT vouchers_history.user_id FROM vouchers_history WHERE vouchers_history.user_id = ? AND vouchers_history.voucher_code = ?)", sqlConnection);
            preparedStatement.setString(1, voucherCode);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, voucherCode);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                boolean isSingleUse = resultSet.getBoolean("is_single_use");
                voucher = new Voucher(resultSet.getInt("credits"), resultSet.getBoolean("allow_new_users"));

                //Get related voucher items
                preparedStatement = Storage.getStorage().prepare("SELECT catalogue_sale_code FROM vouchers_items INNER JOIN catalogue_items ON catalogue_items.sale_code = vouchers_items.catalogue_sale_code WHERE voucher_code = ?", sqlConnection);
                preparedStatement.setString(1, voucherCode);
                resultSet2 = preparedStatement.executeQuery();

                //Find all items
                while (resultSet2.next()) {
                    voucher.getItems().add(resultSet2.getString("catalogue_sale_code"));
                }

                //Delete the voucher and related items if it's single use only
                if (isSingleUse) {
                    preparedStatement = Storage.getStorage().prepare("DELETE FROM vouchers WHERE voucher_code = ?", sqlConnection);
                    preparedStatement.setString(1, voucherCode);
                    preparedStatement.executeQuery();

                    preparedStatement = Storage.getStorage().prepare("DELETE FROM vouchers_items WHERE voucher_code = ?", sqlConnection);
                    preparedStatement.setString(1, voucherCode);
                    preparedStatement.executeQuery();
                }
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);

            if (resultSet2 != null)
                Storage.closeSilently(resultSet2);

            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return voucher;
    }

    public static void logVoucher(String voucherCode, int userId, int creditsRedeemed, List<CatalogueItem> itemsRedeemed) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("INSERT INTO vouchers_history (voucher_code, user_id, credits_redeemed, items_redeemed) VALUES (?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, voucherCode);
            preparedStatement.setInt(2, userId);

            if (creditsRedeemed > 0) {
                preparedStatement.setInt(3, creditsRedeemed);
            } else {
                preparedStatement.setNull(3, Types.NULL);
            }

            if (itemsRedeemed.size() > 0) {
                // Clear all duplicated items
                Map<String, Integer> distinctItems = new HashMap<>();

                for (CatalogueItem item : itemsRedeemed) {
                    if (distinctItems.containsKey(item.getSaleCode())) {
                        distinctItems.put(item.getSaleCode(), distinctItems.get(item.getSaleCode()) + 1);
                    } else {
                        distinctItems.put(item.getSaleCode(), 1);
                    }
                }

                StringBuilder stringBuilder = new StringBuilder();

                for (Map.Entry<String, Integer> kvp : distinctItems.entrySet()) {
                    stringBuilder.append(kvp.getValue());
                    stringBuilder.append(",");
                    stringBuilder.append(kvp.getKey());
                    stringBuilder.append("|");
                }

                preparedStatement.setString(4, stringBuilder.toString().substring(0, stringBuilder.length() - 1));
            } else {
                preparedStatement.setNull(4, Types.NULL);
            }

            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static List<VoucherAdmin> getAdminVouchers() {
        List<VoucherAdmin> vouchers = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT voucher_code, credits, expiry_date, is_single_use, allow_new_users FROM vouchers ORDER BY voucher_code ASC", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                vouchers.add(new VoucherAdmin(
                        resultSet.getString("voucher_code"),
                        resultSet.getInt("credits"),
                        resultSet.getString("expiry_date"),
                        resultSet.getBoolean("is_single_use"),
                        resultSet.getBoolean("allow_new_users")
                ));
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return vouchers;
    }

    public static VoucherAdmin getAdminVoucher(String voucherCode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT voucher_code, credits, expiry_date, is_single_use, allow_new_users FROM vouchers WHERE voucher_code = ?", sqlConnection);
            preparedStatement.setString(1, voucherCode);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new VoucherAdmin(
                        resultSet.getString("voucher_code"),
                        resultSet.getInt("credits"),
                        resultSet.getString("expiry_date"),
                        resultSet.getBoolean("is_single_use"),
                        resultSet.getBoolean("allow_new_users")
                );
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return null;
    }

    public static List<VoucherItemAdmin> getAdminVoucherItems(String voucherCode) {
        List<VoucherItemAdmin> items = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("SELECT voucher_code, catalogue_sale_code FROM vouchers_items WHERE voucher_code = ? ORDER BY catalogue_sale_code ASC", sqlConnection);
            preparedStatement.setString(1, voucherCode);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                items.add(new VoucherItemAdmin(resultSet.getString("voucher_code"), resultSet.getString("catalogue_sale_code")));
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return items;
    }

    public static List<VoucherHistoryAdmin> getAdminVoucherHistory(String voucherCode, int limit) {
        List<VoucherHistoryAdmin> history = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            if (voucherCode != null && !voucherCode.isBlank()) {
                preparedStatement = Storage.getStorage().prepare("SELECT voucher_code, user_id, used_at, credits_redeemed, items_redeemed FROM vouchers_history WHERE voucher_code = ? ORDER BY used_at DESC LIMIT ?", sqlConnection);
                preparedStatement.setString(1, voucherCode);
                preparedStatement.setInt(2, limit);
            } else {
                preparedStatement = Storage.getStorage().prepare("SELECT voucher_code, user_id, used_at, credits_redeemed, items_redeemed FROM vouchers_history ORDER BY used_at DESC LIMIT ?", sqlConnection);
                preparedStatement.setInt(1, limit);
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer creditsRedeemed = resultSet.getObject("credits_redeemed") == null ? null : resultSet.getInt("credits_redeemed");
                history.add(new VoucherHistoryAdmin(
                        resultSet.getString("voucher_code"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("used_at"),
                        creditsRedeemed,
                        resultSet.getString("items_redeemed")
                ));
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return history;
    }

    public static void saveAdminVoucher(VoucherAdmin voucher, List<String> saleCodes, String originalVoucherCode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();

            if (originalVoucherCode != null && !originalVoucherCode.equals(voucher.voucherCode())) {
                preparedStatement = Storage.getStorage().prepare("DELETE FROM vouchers WHERE voucher_code = ?", sqlConnection);
                preparedStatement.setString(1, originalVoucherCode);
                preparedStatement.executeUpdate();

                Storage.closeSilently(preparedStatement);
                preparedStatement = Storage.getStorage().prepare("DELETE FROM vouchers_items WHERE voucher_code = ?", sqlConnection);
                preparedStatement.setString(1, originalVoucherCode);
                preparedStatement.executeUpdate();
                Storage.closeSilently(preparedStatement);
            }

            preparedStatement = Storage.getStorage().prepare("REPLACE INTO vouchers (voucher_code, credits, expiry_date, is_single_use, allow_new_users) VALUES (?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, voucher.voucherCode());
            preparedStatement.setInt(2, voucher.credits());

            if (voucher.expiryDate() == null || voucher.expiryDate().isBlank()) {
                preparedStatement.setNull(3, Types.TIMESTAMP);
            } else {
                preparedStatement.setString(3, voucher.expiryDate());
            }

            preparedStatement.setBoolean(4, voucher.singleUse());
            preparedStatement.setBoolean(5, voucher.allowNewUsers());
            preparedStatement.executeUpdate();
            Storage.closeSilently(preparedStatement);

            preparedStatement = Storage.getStorage().prepare("DELETE FROM vouchers_items WHERE voucher_code = ?", sqlConnection);
            preparedStatement.setString(1, voucher.voucherCode());
            preparedStatement.executeUpdate();
            Storage.closeSilently(preparedStatement);

            preparedStatement = Storage.getStorage().prepare("INSERT INTO vouchers_items (voucher_code, catalogue_sale_code) VALUES (?, ?)", sqlConnection);

            for (String saleCode : saleCodes) {
                if (saleCode == null || saleCode.isBlank()) {
                    continue;
                }

                preparedStatement.setString(1, voucher.voucherCode());
                preparedStatement.setString(2, saleCode.trim());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void deleteAdminVoucher(String voucherCode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Storage.getStorage().getConnection();
            preparedStatement = Storage.getStorage().prepare("DELETE FROM vouchers WHERE voucher_code = ?", sqlConnection);
            preparedStatement.setString(1, voucherCode);
            preparedStatement.executeUpdate();
            Storage.closeSilently(preparedStatement);

            preparedStatement = Storage.getStorage().prepare("DELETE FROM vouchers_items WHERE voucher_code = ?", sqlConnection);
            preparedStatement.setString(1, voucherCode);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
}
