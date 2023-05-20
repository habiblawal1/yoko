/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an \"AS IS\" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.omg.SendingContext;

/**
 * Generated by the JacORB IDL compiler.
 */
public class _CodeBaseStub extends org.omg.CORBA.portable.ObjectImpl implements
		org.omg.SendingContext.CodeBase {
	private String[] ids = { "IDL:omg.org/SendingContext/CodeBase:1.0",
			"IDL:omg.org/SendingContext/RunTime:1.0",
			"IDL:omg.org/CORBA/Object:1.0" };

	public String[] _ids() {
		return ids;
	}

	public final static java.lang.Class _opsClass = org.omg.SendingContext.CodeBaseOperations.class;

	public org.omg.CORBA.Repository get_ir() {
		while (true) {
			if (!this._is_local()) {
				org.omg.CORBA.portable.InputStream _is = null;
				try {
					org.omg.CORBA.portable.OutputStream _os = _request(
							"get_ir", true);
					_is = _invoke(_os);
					org.omg.CORBA.Repository _result = org.omg.CORBA.RepositoryHelper
							.read(_is);
					return _result;
				} catch (org.omg.CORBA.portable.RemarshalException _rx) {
				} catch (org.omg.CORBA.portable.ApplicationException _ax) {
					String _id = _ax.getId();
					throw new RuntimeException("Unexpected exception " + _id);
				} finally {
					this._releaseReply(_is);
				}
			} else {
				org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke(
						"get_ir", _opsClass);
				if (_so == null)
					throw new org.omg.CORBA.UNKNOWN(
							"local invocations not supported!");
				CodeBaseOperations _localServant = (CodeBaseOperations) _so.servant;
				org.omg.CORBA.Repository _result;
				try {
					_result = _localServant.get_ir();
				} finally {
					_servant_postinvoke(_so);
				}
				return _result;
			}

		}

	}

	public java.lang.String[] bases(java.lang.String id) {
		while (true) {
			if (!this._is_local()) {
				org.omg.CORBA.portable.InputStream _is = null;
				try {
					org.omg.CORBA.portable.OutputStream _os = _request("bases",
							true);
					_os.write_string(id);
					_is = _invoke(_os);
					java.lang.String[] _result = org.omg.CORBA.StringSeqHelper
							.read(_is);
					return _result;
				} catch (org.omg.CORBA.portable.RemarshalException _rx) {
				} catch (org.omg.CORBA.portable.ApplicationException _ax) {
					String _id = _ax.getId();
					throw new RuntimeException("Unexpected exception " + _id);
				} finally {
					this._releaseReply(_is);
				}
			} else {
				org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke(
						"bases", _opsClass);
				if (_so == null)
					throw new org.omg.CORBA.UNKNOWN(
							"local invocations not supported!");
				CodeBaseOperations _localServant = (CodeBaseOperations) _so.servant;
				java.lang.String[] _result;
				try {
					_result = _localServant.bases(id);
				} finally {
					_servant_postinvoke(_so);
				}
				return _result;
			}

		}

	}

	public org.omg.CORBA.ValueDefPackage.FullValueDescription meta(
			java.lang.String id) {
		while (true) {
			if (!this._is_local()) {
				org.omg.CORBA.portable.InputStream _is = null;
				try {
					org.omg.CORBA.portable.OutputStream _os = _request("meta",
							true);
					_os.write_string(id);
					_is = _invoke(_os);
					org.omg.CORBA.ValueDefPackage.FullValueDescription _result = org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper
							.read(_is);
					return _result;
				} catch (org.omg.CORBA.portable.RemarshalException _rx) {
				} catch (org.omg.CORBA.portable.ApplicationException _ax) {
					String _id = _ax.getId();
					throw new RuntimeException("Unexpected exception " + _id);
				} finally {
					this._releaseReply(_is);
				}
			} else {
				org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke(
						"meta", _opsClass);
				if (_so == null)
					throw new org.omg.CORBA.UNKNOWN(
							"local invocations not supported!");
				CodeBaseOperations _localServant = (CodeBaseOperations) _so.servant;
				org.omg.CORBA.ValueDefPackage.FullValueDescription _result;
				try {
					_result = _localServant.meta(id);
				} finally {
					_servant_postinvoke(_so);
				}
				return _result;
			}

		}

	}

	public java.lang.String[] implementations(java.lang.String[] ids) {
		while (true) {
			if (!this._is_local()) {
				org.omg.CORBA.portable.InputStream _is = null;
				try {
					org.omg.CORBA.portable.OutputStream _os = _request(
							"implementations", true);
					org.omg.CORBA.StringSeqHelper.write(_os, ids);
					_is = _invoke(_os);
					java.lang.String[] _result = org.omg.SendingContext.CodeBasePackage.URLSeqHelper
							.read(_is);
					return _result;
				} catch (org.omg.CORBA.portable.RemarshalException _rx) {
				} catch (org.omg.CORBA.portable.ApplicationException _ax) {
					String _id = _ax.getId();
					throw new RuntimeException("Unexpected exception " + _id);
				} finally {
					this._releaseReply(_is);
				}
			} else {
				org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke(
						"implementations", _opsClass);
				if (_so == null)
					throw new org.omg.CORBA.UNKNOWN(
							"local invocations not supported!");
				CodeBaseOperations _localServant = (CodeBaseOperations) _so.servant;
				java.lang.String[] _result;
				try {
					_result = _localServant.implementations(ids);
				} finally {
					_servant_postinvoke(_so);
				}
				return _result;
			}

		}

	}

	public org.omg.CORBA.ValueDefPackage.FullValueDescription[] metas(
			java.lang.String id) {
		while (true) {
			if (!this._is_local()) {
				org.omg.CORBA.portable.InputStream _is = null;
				try {
					org.omg.CORBA.portable.OutputStream _os = _request("metas",
							true);
					_os.write_string(id);
					_is = _invoke(_os);
					org.omg.CORBA.ValueDefPackage.FullValueDescription[] _result = org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper
							.read(_is);
					return _result;
				} catch (org.omg.CORBA.portable.RemarshalException _rx) {
				} catch (org.omg.CORBA.portable.ApplicationException _ax) {
					String _id = _ax.getId();
					throw new RuntimeException("Unexpected exception " + _id);
				} finally {
					this._releaseReply(_is);
				}
			} else {
				org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke(
						"metas", _opsClass);
				if (_so == null)
					throw new org.omg.CORBA.UNKNOWN(
							"local invocations not supported!");
				CodeBaseOperations _localServant = (CodeBaseOperations) _so.servant;
				org.omg.CORBA.ValueDefPackage.FullValueDescription[] _result;
				try {
					_result = _localServant.metas(id);
				} finally {
					_servant_postinvoke(_so);
				}
				return _result;
			}

		}

	}

	public java.lang.String implementation(java.lang.String id) {
		while (true) {
			if (!this._is_local()) {
				org.omg.CORBA.portable.InputStream _is = null;
				try {
					org.omg.CORBA.portable.OutputStream _os = _request(
							"implementation", true);
					_os.write_string(id);
					_is = _invoke(_os);
					java.lang.String _result = org.omg.SendingContext.CodeBasePackage.URLHelper
							.read(_is);
					return _result;
				} catch (org.omg.CORBA.portable.RemarshalException _rx) {
				} catch (org.omg.CORBA.portable.ApplicationException _ax) {
					String _id = _ax.getId();
					throw new RuntimeException("Unexpected exception " + _id);
				} finally {
					this._releaseReply(_is);
				}
			} else {
				org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke(
						"implementation", _opsClass);
				if (_so == null)
					throw new org.omg.CORBA.UNKNOWN(
							"local invocations not supported!");
				CodeBaseOperations _localServant = (CodeBaseOperations) _so.servant;
				java.lang.String _result;
				try {
					_result = _localServant.implementation(id);
				} finally {
					_servant_postinvoke(_so);
				}
				return _result;
			}

		}

	}

}
