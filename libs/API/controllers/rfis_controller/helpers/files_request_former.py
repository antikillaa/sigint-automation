from abc import abstractmethod
from tempfile import NamedTemporaryFile
"""
Former classes that helps to form 'files' request attribute for RFi upload
request. Insert into files dict original, approved files if defined, forms
json file.
"""


class FilesFormer(object):

    def __init__(self, json, original, approved):
        self.json = json
        self.original = original
        self.approved = approved
        self._files = None

    def __enter__(self):
        files = self.form_files()
        self._files = files
        return self

    @property
    def files(self):
        return self._files

    def __exit__(self, exc_type, exc_val, exc_tb):
        for u_file in self.files.itervalues():
            fs = u_file[1]
            fs.close()

    def form_files(self):
        approved_former = ApprovedFormer(self.approved, parent_former=None)
        origin_former = OriginalFormer(self.original, approved_former)
        json_former = JsonFormer(self.json, origin_former)
        files_dict = json_former.form_data()
        return files_dict


class Former(object):

    def __init__(self, parent_former=None):
        self.parent_former = parent_former

    @abstractmethod
    def form_data(self):
        pass

    def get_files_from_parent(self):
        if self.parent_former:
            files_dict = self.parent_former.form_data()
        else:
            files_dict = {}

        return files_dict


class TempFilesFormer(Former):

    def __init__(self, approved, parent_former, file_type):
        super(TempFilesFormer, self).__init__(parent_former)
        self.approved = approved
        self.file_type = file_type

    def form_data(self):
        files_dict = self.get_files_from_parent()
        if self.approved:
            app_tmp = NamedTemporaryFile(mode='wb+', prefix=self.approved)
            app_tmp.write(self.file_type)
            app_tmp.seek(0)
            files_dict.update({self.file_type: (self.approved, app_tmp,
                                                'application/octet-stream')})
        return files_dict


class JsonFormer(Former):

    def __init__(self, json, parent_former):
        super(JsonFormer, self).__init__(parent_former)
        self.json = json

    def form_data(self):
        files_dict = self.get_files_from_parent()
        json_tmp = NamedTemporaryFile(suffix="test.json")
        import json
        json.dump(self.json, json_tmp)
        json_tmp.seek(0)
        files_dict.update({"json": ('blob', json_tmp, 'application/json')})
        return files_dict


class ApprovedFormer(TempFilesFormer):

    def __init__(self, approved, parent_former):
        super(ApprovedFormer, self).__init__(approved, parent_former,"approved")


class OriginalFormer(TempFilesFormer):

    def __init__(self, original, parent_former):
        super(OriginalFormer, self).__init__(
                original, parent_former, "original")