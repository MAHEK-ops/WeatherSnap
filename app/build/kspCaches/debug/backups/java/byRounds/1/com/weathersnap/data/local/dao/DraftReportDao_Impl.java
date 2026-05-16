package com.weathersnap.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.weathersnap.data.local.entity.DraftReportEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DraftReportDao_Impl implements DraftReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DraftReportEntity> __insertionAdapterOfDraftReportEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearDraft;

  public DraftReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDraftReportEntity = new EntityInsertionAdapter<DraftReportEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `draft_report` (`id`,`weatherJson`,`imagePath`,`originalSizeKb`,`compressedSizeKb`,`notes`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DraftReportEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getWeatherJson());
        statement.bindString(3, entity.getImagePath());
        statement.bindLong(4, entity.getOriginalSizeKb());
        statement.bindLong(5, entity.getCompressedSizeKb());
        statement.bindString(6, entity.getNotes());
      }
    };
    this.__preparedStmtOfClearDraft = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM draft_report WHERE id = 1";
        return _query;
      }
    };
  }

  @Override
  public Object saveDraft(final DraftReportEntity draft,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDraftReportEntity.insert(draft);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearDraft(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearDraft.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearDraft.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getDraft(final Continuation<? super DraftReportEntity> $completion) {
    final String _sql = "SELECT * FROM draft_report WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DraftReportEntity>() {
      @Override
      @Nullable
      public DraftReportEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWeatherJson = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherJson");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfOriginalSizeKb = CursorUtil.getColumnIndexOrThrow(_cursor, "originalSizeKb");
          final int _cursorIndexOfCompressedSizeKb = CursorUtil.getColumnIndexOrThrow(_cursor, "compressedSizeKb");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final DraftReportEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpWeatherJson;
            _tmpWeatherJson = _cursor.getString(_cursorIndexOfWeatherJson);
            final String _tmpImagePath;
            _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            final long _tmpOriginalSizeKb;
            _tmpOriginalSizeKb = _cursor.getLong(_cursorIndexOfOriginalSizeKb);
            final long _tmpCompressedSizeKb;
            _tmpCompressedSizeKb = _cursor.getLong(_cursorIndexOfCompressedSizeKb);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _result = new DraftReportEntity(_tmpId,_tmpWeatherJson,_tmpImagePath,_tmpOriginalSizeKb,_tmpCompressedSizeKb,_tmpNotes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
